import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
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

// PortfolioDetails w MUI
const PortfolioDetails = ({ portfolio, onBack }) => (
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
    <Button variant="outlined" onClick={onBack} sx={{ mb: 3 }}>
      ← Powrót do portfeli
    </Button>
    <Typography variant="h4" gutterBottom>{portfolio.name}</Typography>
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell><b>Spółka</b></TableCell>
            <TableCell><b>Liczba akcji</b></TableCell>
            <TableCell><b>Cena bieżąca</b></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {portfolio.details.map((item, idx) => (
            <TableRow key={idx}>
              <TableCell>{item.symbol}</TableCell>
              <TableCell>{item.shares}</TableCell>
              <TableCell>{item.price}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  </Box>
);

const PortfolioList = () => {
  const [portfolios, setPortfolios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPortfolios = async () => {
      setLoading(true);
      try {
        const response = await fetch("http://localhost:8081/portfolio/all");
        if (!response.ok) throw new Error("Błąd pobierania portfeli");
        const data = await response.json();
        setPortfolios(data);
        setError(null);
      } catch (err) {
        setError(err.message);
      }
      setLoading(false);
    };
    fetchPortfolios();
  }, []);

  if (loading) {
    return (
      <Stack alignItems="center" mt={8}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }}>Ładowanie portfeli...</Typography>
      </Stack>
    );
  }
  if (error)
    return (
      <Box maxWidth={600} mx="auto" mt={6}>
        <Alert severity="error">Błąd: {error}</Alert>
      </Box>
    );

  if (selected !== null) {
    const portfolio = portfolios[selected];
    return (
      <PortfolioDetails
        portfolio={portfolio}
        onBack={() => setSelected(null)}
      />
    );
  }

  return (
    <Box
      component={Paper}
      elevation={3}
      sx={{
        mt: 5,
        p: 4,
        maxWidth: 900,
        mx: "auto"
      }}
    >
      <Stack direction="row" justifyContent="space-between" mb={2}>
        <Typography variant="h4" sx={{ fontWeight: 600 }}>
          Twoje portfolia
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate("/portfolio")}
        >
          Dodaj nowe portfolio
        </Button>
      </Stack>
      {portfolios.length === 0 ? (
        <Alert severity="info" sx={{ my: 2 }}>
          Brak portfeli do wyświetlenia.
        </Alert>
      ) : (
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><b>Nazwa portfela</b></TableCell>
                <TableCell><b>Wartość</b></TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {portfolios.map((portf, idx) => (
                <TableRow hover key={portf.id}>
                  <TableCell>{portf.name}</TableCell>
                  <TableCell>{portf.value} zł</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      onClick={() => setSelected(idx)}
                    >
                      Detale
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );
};

export default PortfolioList;
