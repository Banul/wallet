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

    // Pierwsze pobranie od razu
    fetchPortfolioValue();

    // Następnie co 30 sekund
    interval = setInterval(fetchPortfolioValue, 30000);

    return () => clearInterval(interval);
  }, [id]);

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

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis 
            dataKey="time" 
            label={{ value: 'Czas', position: 'insideBottom', offset: -5 }}
          />
          <YAxis 
            label={{ value: 'Wartość (zł)', angle: -90, position: 'insideLeft' }}
          />
          <Tooltip />
          <Legend />
          <Line 
            type="monotone" 
            dataKey="value" 
            stroke="#8884d8" 
            strokeWidth={2}
            name="Wartość portfolio"
            dot={{ r: 4 }}
            activeDot={{ r: 6 }}
          />
        </LineChart>
      </ResponsiveContainer>

      {chartData.length > 0 && (
        <Box mt={3} p={2} bgcolor="grey.100" borderRadius={1}>
          <Typography variant="body1">
            <b>Aktualna wartość:</b> {chartData[chartData.length - 1].value.toFixed(2)} zł
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Liczba pomiarów: {chartData.length}
          </Typography>
        </Box>
      )}
    </Box>
  );
};

export default LivePortfolioChart;
