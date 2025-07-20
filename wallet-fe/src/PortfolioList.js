import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const PortfolioDetails = ({ portfolio, onBack }) => (
  <div>
    <button onClick={onBack}>← Powrót do portfeli</button>
    <h2>{portfolio.name}</h2>
    <table>
      <thead>
        <tr>
          <th>Spółka</th>
          <th>Liczba akcji</th>
          <th>Cena bieżąca</th>
        </tr>
      </thead>
      <tbody>
        {portfolio.details.map((item, idx) => (
          <tr key={idx}>
            <td>{item.symbol}</td>
            <td>{item.shares}</td>
            <td>{item.price}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
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

  if (loading) return <div>Ładowanie portfeli...</div>;
  if (error) return <div>Błąd: {error}</div>;

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
    <div>
      <button
        style={{
          marginBottom: '20px',
          padding: '10px 16px',
          fontSize: '16px',
          cursor: 'pointer'
        }}
        onClick={() => navigate('/portfolio')}
      >
        Dodaj nowe portfolio
      </button>
      <h1>Twoje portfolia</h1>
      {portfolios.length === 0 ? (
        <div>Brak portfeli do wyświetlenia.</div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Nazwa portfela</th>
              <th>Wartość</th>
              <th>Wynik (%)</th>
              <th>Liczba aktywów</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {portfolios.map((portf, idx) => (
              <tr key={portf.id}>
                <td>{portf.name}</td>
                <td>{portf.value} zł</td>
                <td style={{
                  color: portf.percentage >= 0 ? "green" : "red"
                }}>
                  {portf.percentage}%
                </td>
                <td>{portf.itemCount}</td>
                <td>
                  <button onClick={() => setSelected(idx)}>Detale</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PortfolioList;
