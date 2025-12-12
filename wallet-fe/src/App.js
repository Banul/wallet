import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PortfolioList from "./PortfolioList";
import Portfolio from "./Portfolio";
import PortfolioDetailPage from "./PortfolioDetailPage";
import LivePortfolioChart from "./LivePortfolioChart";
import PortfolioStatisticsMap from "./PortfolioStatisticsMap";
import PortfolioIndustryStatistics from "./PortfolioIndustryStatistics";


function App() {
  return (
    <Router>
      <Routes>
        {/* Strona główna z listą portfeli */}
        <Route path="/" element={<PortfolioList />} />
        {/* Strona do dodawania nowego portfela */}
        <Route path="/portfolio" element={<Portfolio />} />
        <Route path="/portfolio-details/:id" element={<PortfolioDetailPage />} />
        <Route path="/portfolio-live/:id" element={<LivePortfolioChart />} />
        <Route path="/portfolio-statistics/:id" element={<PortfolioStatisticsMap />} />
        <Route path="/portfolio-industry-statistics/:id" element={<PortfolioIndustryStatistics />} />
      </Routes>
    </Router>
  );
}

export default App;
