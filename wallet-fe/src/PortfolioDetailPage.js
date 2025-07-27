import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Box,
  Button,
  Paper,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  CircularProgress,
  Alert,
  Stack
} from "@mui/material";

const PortfolioDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [details, setDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDetails = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:8081/portfolio-details/${id}`);
        console.log(response)
        if (!response.ok) throw new Error("Błąd pobierania detali portfela");
        const data = await response.json();
        console.log("Fetched portfolio details:", data);
        
        setDetails(data);
        setError(null);
      } catch (err) {
        setError(err.message);
      }
      setLoading(false);
    };
    fetchDetails();
  }, [id]);

  if (loading) {
    return (
      <Stack alignItems="center" mt={8}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }}>Ładowanie detali portfela...</Typography>
      </Stack>
    );
  }

  if (error) {
    return (
      <Box maxWidth={600} mx="auto" mt={6}>
        <Alert severity="error">Błąd: {error}</Alert>
        <Button variant="contained" sx={{ mt: 2 }} onClick={() => navigate(-1)}>
          Powrót
        </Button>
      </Box>
    );
  }

  if (!details) {
    return null; // albo loading spinner
  }

  return (
    <Box
      component={Paper}
      sx={{
        mt: 5,
        p: 4,
        maxWidth: 800,
        mx: "auto"
      }}
      elevation={3}
    >
      <Button variant="outlined" onClick={() => navigate(-1)} sx={{ mb: 3 }}>
        ← Powrót do portfeli
      </Button>
      <Typography variant="h4" gutterBottom>
        Szczegóły portfela
      </Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell><b>Spółka (ticker)</b></TableCell>
              <TableCell><b>Sektor</b></TableCell>
              <TableCell><b>Branża (Industry)</b></TableCell>
              <TableCell><b>Kraj</b></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {details.stockDetails.map((stock, idx) => (
              <TableRow key={idx}>
                <TableCell>{stock.ticker}</TableCell>
                <TableCell>{stock.sector}</TableCell>
                <TableCell>{stock.industry}</TableCell>
                <TableCell>{stock.country}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default PortfolioDetailPage;
