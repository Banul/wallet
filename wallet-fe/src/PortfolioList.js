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

const PortfolioList = () => {
  const [portfolios, setPortfolios] = useState([]);
  const [loading, setLoading] = useState(true);
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
  if (error) {
    return (
      <Box maxWidth={600} mx="auto" mt={6}>
        <Alert severity="error">Błąd: {error}</Alert>
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
              {portfolios.map((portf) => (
                <TableRow hover key={portf.id}>
                  <TableCell>{portf.name}</TableCell>
                  <TableCell>{portf.value} zł</TableCell>
                  <TableCell>
                    <Stack direction="row" spacing={1}>
                      <Button
                        variant="outlined"
                        onClick={() => navigate(`/portfolio-details/${portf.id}`)}
                      >
                        Detale
                      </Button>
                      <Button
                        variant="contained"
                        color="secondary"
                        onClick={() => navigate(`/portfolio-live/${portf.id}`)}
                      >
                        Śledź na żywo
                      </Button>
                    </Stack>
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
