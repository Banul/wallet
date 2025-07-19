import React, { useState } from 'react';
import { 
  Container, Typography, TextField, Button, 
  Table, TableBody, TableCell, TableContainer, 
  TableHead, TableRow, Paper 
} from '@mui/material';

function Portfolio() {
  const [ticker, setTicker] = useState('');
  const [amount, setAmount] = useState('');
  const [stocks, setStocks] = useState([]);
  // hooki na górze komponentu!
  const [loading, setLoading] = useState(false);
  const [responseMsg, setResponseMsg] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (ticker && amount && !isNaN(amount)) {
      setStocks([...stocks, { ticker: ticker.toUpperCase(), amount: Number(amount) }]);
      setTicker('');
      setAmount('');
    }
  };

  const handleCreatePortfolio = async () => {
    setLoading(true);
    setResponseMsg('');
    try {
      const res = await fetch('http://localhost:8081/portfolio', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ assets: stocks }),
      });
      if (res.ok) {
        setResponseMsg('Portfel został utworzony!');
      } else {
        setResponseMsg('Błąd podczas tworzenia portfela.');
      }
    } catch (e) {
      setResponseMsg('Wystąpił błąd sieci.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ marginTop: 6, padding: 3, bgcolor: 'background.paper', borderRadius: 2, boxShadow: 3 }}>
      <Typography variant="h4" align="center" gutterBottom color="primary">
        Your Portfolio
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Ticker"
          variant="outlined"
          fullWidth
          margin="normal"
          value={ticker}
          onChange={(e) => setTicker(e.target.value)}
          required
          inputProps={{ style: { textTransform: 'uppercase' } }}
        />
        <TextField
          label="Ilość"
          variant="outlined"
          type="number"
          fullWidth
          margin="normal"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
          inputProps={{ min: 1 }}
        />
        <Button 
          type="submit" 
          variant="contained" 
          color="primary" 
          fullWidth 
          sx={{ mt: 2, mb: 3 }}
        >
          Add Stock
        </Button>
      </form>
      {stocks.length > 0 && (
        <TableContainer component={Paper} sx={{ mt: 3 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><b>Ticker</b></TableCell>
                <TableCell><b>Ilość</b></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {stocks.map((stock, idx) => (
                <TableRow 
                  key={idx} 
                  sx={{ bgcolor: idx % 2 === 0 ? 'action.hover' : 'background.default' }}
                >
                  <TableCell>{stock.ticker}</TableCell>
                  <TableCell>{stock.amount}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      {/* Tu dodajemy przycisk i komunikat */}
      <Button
        variant="contained"
        color="secondary"
        fullWidth
        sx={{ mt: 2 }}
        onClick={handleCreatePortfolio}
        disabled={stocks.length === 0 || loading}
      >
        {loading ? 'Tworzenie...' : 'Create Portfolio'}
      </Button>
      {responseMsg && (
        <Typography align="center" sx={{ mt: 2 }}>
          {responseMsg}
        </Typography>
      )}
    </Container>
  );
}

export default Portfolio;
