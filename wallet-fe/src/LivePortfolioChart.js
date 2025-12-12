import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Box,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Button,
  Stack
} from "@mui/material";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer
} from "recharts";

const LivePortfolioChart = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [portfolioName, setPortfolioName] = useState("");

  useEffect(() => {
    let interval;

    const fetchPortfolioValue = async () => {
      try {
        const response = await fetch(`http://localhost:8081/portfolio/${id}`);
        if (!response.ok) throw new Error("Błąd pobierania danych portfolio");
        
        const data = await response.json();
        const now = new Date();
        const timeLabel = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}:${String(now.getSeconds()).padStart(2, '0')}`;

        setPortfolioName(data.name);
        setChartData((prev) => [
          ...prev,
          {
            time: timeLabel,
            value: parseFloat(data.value)
          }
        ]);
        
        setError(null);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchPortfolioValue();
    interval = setInterval(fetchPortfolioValue, 30000);

    return () => clearInterval(interval);
  }, [id]);

  // Oblicz zakres dla osi Y z buforem
  const getYAxisDomain = () => {
    if (chartData.length === 0) return [0, 100];
    
    const values = chartData.map(d => d.value);
    const min = Math.min(...values);
    const max = Math.max(...values);
    const range = max - min;
    
    // Jeśli zakres jest bardzo mały, dodaj większy bufor
    const buffer = range < 1000 ? 500 : range * 0.1;
    
    return [min - buffer, max + buffer];
  };

  // Oblicz zmianę i kolor
  const getChangeInfo = () => {
    if (chartData.length < 2) return null;
    
    const change = chartData[chartData.length - 1].value - chartData[0].value;
    let color;
    
    if (change > 0) {
      color = '#2e7d32'; // zielony
    } else if (change < 0) {
      color = '#d32f2f'; // czerwony
    } else {
      color = '#000000'; // czarny
    }
    
    return {
      value: change,
      color: color
    };
  };

  if (loading) {
    return (
      <Stack alignItems="center" mt={8}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }}>Ładowanie wykresu...</Typography>
      </Stack>
    );
  }

  if (error) {
    return (
      <Box maxWidth={600} mx="auto" mt={6}>
        <Alert severity="error">Błąd: {error}</Alert>
        <Button sx={{ mt: 2 }} variant="outlined" onClick={() => navigate(-1)}>
          Powrót do listy
        </Button>
      </Box>
    );
  }

  const yAxisDomain = getYAxisDomain();
  const changeInfo = getChangeInfo();

  return (
    <Box
      component={Paper}
      elevation={3}
      sx={{
        mt: 5,
        p: 4,
        maxWidth: 1200,
        mx: "auto"
      }}
    >
      <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 600 }}>
          Śledzenie na żywo: {portfolioName}
        </Typography>
        <Button variant="outlined" onClick={() => navigate(-1)}>
          Powrót do listy
        </Button>
      </Stack>

      <Typography variant="body2" color="text.secondary" mb={2}>
        Wykres aktualizuje się co 30 sekund
      </Typography>

      <ResponsiveContainer width="100%" height={500}>
        <LineChart 
          data={chartData}
          margin={{ top: 20, right: 30, left: 70, bottom: 20 }}
        >
          <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
          <XAxis 
            dataKey="time" 
            label={{ value: 'Czas', position: 'insideBottom', offset: -10 }}
            tick={{ fontSize: 12 }}
          />
          <YAxis 
            domain={yAxisDomain}
            tickFormatter={(value) => value.toLocaleString('pl-PL', { 
              minimumFractionDigits: 2,
              maximumFractionDigits: 2 
            })}
            label={{ 
              value: 'Wartość (zł)', 
              angle: -90, 
              position: 'insideLeft',
              style: { textAnchor: 'middle' }
            }}
            tick={{ fontSize: 12 }}
            width={80}
          />
          <Tooltip 
            formatter={(value) => [
              value.toLocaleString('pl-PL', { 
                minimumFractionDigits: 2,
                maximumFractionDigits: 2 
              }) + ' zł',
              'Wartość portfolio'
            ]}
            contentStyle={{ 
              backgroundColor: 'rgba(255, 255, 255, 0.95)',
              border: '1px solid #ccc',
              borderRadius: '4px'
            }}
          />
          <Legend />
          <Line 
            type="monotone" 
            dataKey="value" 
            stroke="#1976d2" 
            strokeWidth={3}
            name="Wartość portfolio"
            dot={{ r: 5, fill: '#1976d2' }}
            activeDot={{ r: 7 }}
          />
        </LineChart>
      </ResponsiveContainer>

      {chartData.length > 0 && (
        <Box mt={3} p={2} bgcolor="grey.100" borderRadius={1}>
          <Typography variant="body1">
            <b>Aktualna wartość:</b> {chartData[chartData.length - 1].value.toLocaleString('pl-PL', { 
              minimumFractionDigits: 2,
              maximumFractionDigits: 2 
            })} zł
          </Typography>
          <Typography variant="body2" color="text.secondary" mt={1}>
            Liczba pomiarów: {chartData.length}
          </Typography>
          {changeInfo && (
            <Typography 
              variant="body2" 
              sx={{ 
                color: changeInfo.color,
                fontWeight: 600,
                mt: 1
              }}
            >
              Zmiana: {changeInfo.value.toLocaleString('pl-PL', { 
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
                signDisplay: 'always'
              })} zł
            </Typography>
          )}
        </Box>
      )}
    </Box>
  );
};

export default LivePortfolioChart;
