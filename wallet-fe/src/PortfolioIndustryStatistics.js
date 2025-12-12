import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Box,
  Button,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Stack,
  Card,
  CardContent
} from "@mui/material";
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

const COLORS = [
  "#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#8884d8",
  "#82ca9d", "#ffc658", "#ff7c7c", "#8dd1e1", "#d084d0"
];

const PortfolioIndustryStatistics = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [statistics, setStatistics] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStatistics = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:8081/portfolio-statistics/${id}`);
        if (!response.ok) throw new Error("Błąd pobierania statystyk");
        const data = await response.json();
        setStatistics(data);
        setError(null);
      } catch (err) {
        setError(err.message);
      }
      setLoading(false);
    };
    fetchStatistics();
  }, [id]);

  if (loading) {
    return (
      <Stack alignItems="center" mt={8}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }}>Ładowanie statystyk...</Typography>
      </Stack>
    );
  }

  if (error) {
    return (
      <Box maxWidth={600} mx="auto" mt={6}>
        <Alert severity="error">Błąd: {error}</Alert>
        <Button
          variant="outlined"
          startIcon={<ArrowBackIcon />}
          onClick={() => navigate("/")}
          sx={{ mt: 2 }}
        >
          Powrót do listy
        </Button>
      </Box>
    );
  }

  const industryData = statistics?.industryShares?.map((item) => ({
    name: item.industry,
    value: parseFloat(item.percentageValue)
  })) || [];

  const customLabel = ({ name, value }) => {
    return `${name}: ${value.toFixed(1)}%`;
  };

  return (
    <Box
      component={Paper}
      elevation={3}
      sx={{
        mt: 5,
        p: 4,
        maxWidth: 1000,
        mx: "auto"
      }}
    >
      <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 600 }}>
          Statystyki branżowe
        </Typography>
        <Button
          variant="outlined"
          startIcon={<ArrowBackIcon />}
          onClick={() => navigate("/")}
        >
          Powrót
        </Button>
      </Stack>

      {industryData.length === 0 ? (
        <Alert severity="info">Brak danych do wyświetlenia</Alert>
      ) : (
        <Card sx={{ mt: 3 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom align="center">
              Rozkład inwestycji według branży
            </Typography>
            <ResponsiveContainer width="100%" height={400}>
              <PieChart>
                <Pie
                  data={industryData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={customLabel}
                  outerRadius={120}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {industryData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(value) => `${value.toFixed(2)}%`} />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      )}
    </Box>
  );
};

export default PortfolioIndustryStatistics;