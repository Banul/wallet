import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PortfolioList from "./PortfolioList";
import Portfolio from "./Portfolio";

function App() {
  return (
    <Router>
      <Routes>
        {/* Strona główna z listą portfeli */}
        <Route path="/" element={<PortfolioList />} />
        {/* Strona do dodawania nowego portfela */}
        <Route path="/portfolio" element={<Portfolio />} />
      </Routes>
    </Router>
  );
}

export default App;
