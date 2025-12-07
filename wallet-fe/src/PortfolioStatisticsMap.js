import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  ComposableMap,
  Geographies,
  Geography,
} from "react-simple-maps";
import {
  Box,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Stack,
  IconButton,
  Card,
  CardContent,
  Divider,
  Container,
  Chip,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import PublicIcon from "@mui/icons-material/Public";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";

const geoUrl = "https://cdn.jsdelivr.net/npm/world-atlas@2/countries-110m.json";

const COUNTRY_CODE_MAP = {
  USA: "840", POL: "616", DEU: "276", GBR: "826", FRA: "250", JPN: "392",
  CHN: "156", CAN: "124", AUS: "036", IND: "356", BRA: "076", KOR: "410",
  NLD: "528", CHE: "756", SWE: "752", ESP: "724", ITA: "380", MEX: "484",
  NOR: "578", DNK: "208", FIN: "246", BEL: "056", AUT: "040", IRL: "372",
  ISR: "376", SGP: "702", HKG: "344", TWN: "158", ZAF: "710", RUS: "643",
  ARG: "032", CHL: "152", THA: "764", MYS: "458", IDN: "360", PHL: "608",
  NZL: "554", PRT: "620", GRC: "300", TUR: "792", SAU: "682", ARE: "784",
  URY: "858"
};

const PortfolioStatisticsMap = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [statistics, setStatistics] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [hoveredCountry, setHoveredCountry] = useState(null);

  useEffect(() => {
    const fetchStatistics = async () => {
      setLoading(true);
      try {
        const response = await fetch(
          `http://localhost:8081/portfolio-statistics/${id}`
        );
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

  const getColorForPercentage = (percentage) => {
    if (percentage === 0) return "#E8EAF6";
    if (percentage < 5) return "#90CAF9";
    if (percentage < 10) return "#42A5F5";
    if (percentage < 20) return "#1E88E5";
    if (percentage < 30) return "#1565C0";
    return "#0D47A1";
  };

  const countryDataMap = {};
  statistics?.countryShares?.forEach((share) => {
    const isoCode = COUNTRY_CODE_MAP[share.countryCode];
    if (isoCode) {
      countryDataMap[isoCode] = {
        percentage: share.percentageValue,
        name: share.countryName,
        code: share.countryCode,
      };
    }
  });

  const totalCountries = statistics?.countryShares?.length || 0;
  const topCountry = statistics?.countryShares?.sort(
    (a, b) => b.percentageValue - a.percentageValue
  )[0];

  if (loading) {
    return (
      <Box display="flex" alignItems="center" justifyContent="center" minHeight="100vh" bgcolor="#FAFAFA">
        <Stack alignItems="center" spacing={2}>
          <CircularProgress size={60} thickness={4} />
          <Typography variant="h6" color="text.secondary">
            Ładowanie statystyk...
          </Typography>
        </Stack>
      </Box>
    );
  }

  if (error) {
    return (
      <Container maxWidth="md" sx={{ mt: 8 }}>
        <Alert severity="error" variant="filled">
          {error}
        </Alert>
      </Container>
    );
  }

  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "#FAFAFA" }}>
      {/* Fixed Header */}
      <Box 
        sx={{ 
          bgcolor: "white", 
          borderBottom: "1px solid #E0E0E0",
          position: "sticky",
          top: 0,
          zIndex: 10,
          boxShadow: "0 2px 4px rgba(0,0,0,0.04)"
        }}
      >
        <Container maxWidth="xl" sx={{ py: 2 }}>
          <Stack direction="row" alignItems="center" spacing={2}>
            <IconButton 
              onClick={() => navigate(-1)}
              sx={{ 
                bgcolor: "#F5F5F5",
                "&:hover": { bgcolor: "#E0E0E0" }
              }}
            >
              <ArrowBackIcon />
            </IconButton>
            <Box>
              <Typography 
                variant="h4" 
                sx={{ 
                  fontWeight: 700,
                  letterSpacing: "-0.5px",
                  fontFamily: "'Inter', 'Roboto', sans-serif"
                }}
              >
                Statystyki portfela
              </Typography>
            </Box>
          </Stack>
        </Container>
      </Box>

      <Container maxWidth="xl" sx={{ py: 4 }}>
        {/* Stats Cards Row - Full Width */}
        <Stack direction={{ xs: "column", md: "row" }} spacing={3} mb={4}>
          <Card 
            elevation={0}
            sx={{ 
              flex: 1,
              borderRadius: 2,
              border: "1px solid #E0E0E0",
              transition: "all 0.2s",
              "&:hover": { 
                boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
                transform: "translateY(-2px)"
              }
            }}
          >
            <CardContent sx={{ p: 3 }}>
              <Stack direction="row" alignItems="center" spacing={2}>
                <Box 
                  sx={{ 
                    width: 56, 
                    height: 56, 
                    borderRadius: 2,
                    bgcolor: "#E3F2FD",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center"
                  }}
                >
                  <PublicIcon sx={{ fontSize: 28, color: "#1976D2" }} />
                </Box>
                <Box>
                  <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
                    Liczba krajów
                  </Typography>
                  <Typography variant="h3" fontWeight="700">
                    {totalCountries}
                  </Typography>
                </Box>
              </Stack>
            </CardContent>
          </Card>

          <Card 
            elevation={0}
            sx={{ 
              flex: 1,
              borderRadius: 2,
              border: "1px solid #E0E0E0",
              transition: "all 0.2s",
              "&:hover": { 
                boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
                transform: "translateY(-2px)"
              }
            }}
          >
            <CardContent sx={{ p: 3 }}>
              <Stack direction="row" alignItems="center" spacing={2}>
                <Box 
                  sx={{ 
                    width: 56, 
                    height: 56, 
                    borderRadius: 2,
                    bgcolor: "#F3E5F5",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center"
                  }}
                >
                  <TrendingUpIcon sx={{ fontSize: 28, color: "#7B1FA2" }} />
                </Box>
                <Box>
                  <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
                    Największy udział
                  </Typography>
                  <Typography variant="h5" fontWeight="700">
                    {topCountry?.countryName || "N/A"}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {topCountry?.percentageValue.toFixed(1)}%
                  </Typography>
                </Box>
              </Stack>
            </CardContent>
          </Card>

          <Card 
            elevation={0}
            sx={{ 
              flex: 1,
              borderRadius: 2,
              border: "1px solid #E0E0E0",
              transition: "all 0.2s",
              "&:hover": { 
                boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
                transform: "translateY(-2px)"
              }
            }}
          >
            <CardContent sx={{ p: 3 }}>
              <Stack direction="row" alignItems="center" spacing={2}>
                <Box 
                  sx={{ 
                    width: 56, 
                    height: 56, 
                    borderRadius: 2,
                    bgcolor: "#E8F5E9",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center"
                  }}
                >
                  <AccountBalanceIcon sx={{ fontSize: 28, color: "#388E3C" }} />
                </Box>
                <Box>
                  <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
                    Dywersyfikacja
                  </Typography>
                  <Typography variant="h5" fontWeight="700">
                    {totalCountries > 10 ? "Wysoka" : totalCountries > 5 ? "Średnia" : "Niska"}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {totalCountries} rynków
                  </Typography>
                </Box>
              </Stack>
            </CardContent>
          </Card>
        </Stack>

        {/* Main Content - Mapa + Lista obok siebie */}
        <Stack direction={{ xs: "column", lg: "row" }} spacing={3}>
          {/* Mapa - 60% szerokości */}
          <Paper 
            elevation={0}
            sx={{ 
              flex: "1 1 60%",
              borderRadius: 2,
              border: "1px solid #E0E0E0",
              overflow: "hidden"
            }}
          >
            <Box sx={{ p: 3, borderBottom: "1px solid #F0F0F0" }}>
              <Stack direction="row" justifyContent="space-between" alignItems="center">
                <Typography 
                  variant="h6" 
                  fontWeight="600"
                  sx={{ fontFamily: "'Inter', 'Roboto', sans-serif" }}
                >
                  Rozkład geograficzny
                </Typography>
                {hoveredCountry && (
                  <Chip 
                    label={`${hoveredCountry.name}: ${hoveredCountry.percentage.toFixed(2)}%`}
                    size="small"
                    sx={{ 
                      fontWeight: 600,
                      bgcolor: "#1976D2",
                      color: "white"
                    }}
                  />
                )}
              </Stack>
            </Box>

            {/* Legend */}
            <Box sx={{ px: 3, py: 2, bgcolor: "#FAFAFA", borderBottom: "1px solid #F0F0F0" }}>
              <Stack direction="row" spacing={3} flexWrap="wrap" useFlexGap>
                {[
                  { color: "#E8EAF6", label: "0%" },
                  { color: "#90CAF9", label: "<5%" },
                  { color: "#42A5F5", label: "5-10%" },
                  { color: "#1E88E5", label: "10-20%" },
                  { color: "#1565C0", label: "20-30%" },
                  { color: "#0D47A1", label: ">30%" },
                ].map((item) => (
                  <Stack key={item.label} direction="row" alignItems="center" spacing={1}>
                    <Box 
                      sx={{ 
                        width: 20, 
                        height: 20, 
                        bgcolor: item.color,
                        borderRadius: 0.5,
                        border: "1px solid rgba(0,0,0,0.1)"
                      }} 
                    />
                    <Typography variant="caption" fontWeight="500">
                      {item.label}
                    </Typography>
                  </Stack>
                ))}
              </Stack>
            </Box>

            {/* Map */}
            <Box sx={{ p: 3, bgcolor: "white" }}>
              <Box 
                sx={{ 
                  bgcolor: "#F8F9FA",
                  borderRadius: 1,
                  overflow: "hidden"
                }}
              >
                <ComposableMap
                  projection="geoMercator"
                  projectionConfig={{ scale: 147 }}
                  height={550}
                >
                  <Geographies geography={geoUrl}>
                    {({ geographies }) =>
                      geographies.map((geo) => {
                        const countryData = countryDataMap[geo.id];
                        const percentage = countryData?.percentage || 0;
                        const countryName = countryData?.name || geo.properties.name;

                        return (
                          <Geography
                            key={geo.rsmKey}
                            geography={geo}
                            fill={getColorForPercentage(percentage)}
                            stroke="#FFFFFF"
                            strokeWidth={0.5}
                            onMouseEnter={() => {
                              if (countryData) {
                                setHoveredCountry({
                                  name: countryName,
                                  percentage: percentage,
                                });
                              }
                            }}
                            onMouseLeave={() => setHoveredCountry(null)}
                            style={{
                              default: { outline: "none" },
                              hover: {
                                fill: percentage > 0 ? "#FF6B35" : "#E8EAF6",
                                outline: "none",
                                cursor: percentage > 0 ? "pointer" : "default",
                              },
                              pressed: { outline: "none" },
                            }}
                          />
                        );
                      })
                    }
                  </Geographies>
                </ComposableMap>
              </Box>
            </Box>
          </Paper>

          {/* Top Countries - 40% szerokości */}
          <Paper 
            elevation={0}
            sx={{ 
              flex: "1 1 40%",
              borderRadius: 2,
              border: "1px solid #E0E0E0",
              overflow: "hidden",
              maxHeight: "820px"
            }}
          >
            <Box sx={{ p: 3, borderBottom: "1px solid #F0F0F0", bgcolor: "white", position: "sticky", top: 0, zIndex: 1 }}>
              <Typography 
                variant="h6" 
                fontWeight="600"
                sx={{ fontFamily: "'Inter', 'Roboto', sans-serif" }}
              >
                Ranking krajów
              </Typography>
            </Box>

            <Box sx={{ p: 2, overflowY: "auto", maxHeight: "720px" }}>
              <Stack spacing={1.5}>
                {statistics?.countryShares
                  ?.sort((a, b) => b.percentageValue - a.percentageValue)
                  .map((share, index) => {
                    const isTop3 = index < 3;
                    return (
                      <Box
                        key={share.countryCode}
                        sx={{
                          p: 2,
                          bgcolor: isTop3 ? "#F5F5FF" : "white",
                          border: isTop3 ? "1.5px solid #5C6BC0" : "1px solid #E0E0E0",
                          borderRadius: 1.5,
                          transition: "all 0.15s",
                          cursor: "pointer",
                          "&:hover": {
                            bgcolor: isTop3 ? "#EBEBFF" : "#F9F9F9",
                            transform: "translateX(4px)",
                          },
                        }}
                      >
                        <Stack direction="row" alignItems="center" spacing={2}>
                          <Box
                            sx={{
                              minWidth: 32,
                              height: 32,
                              borderRadius: 1,
                              bgcolor: isTop3 ? "#5C6BC0" : "#9E9E9E",
                              color: "white",
                              display: "flex",
                              alignItems: "center",
                              justifyContent: "center",
                              fontWeight: 700,
                              fontSize: 14,
                            }}
                          >
                            {index + 1}
                          </Box>
                          <Box sx={{ flex: 1, minWidth: 0 }}>
                            <Typography variant="body1" fontWeight="600" noWrap>
                              {share.countryName}
                            </Typography>
                            <Typography variant="caption" color="text.secondary">
                              {share.countryCode}
                            </Typography>
                          </Box>
                          <Typography 
                            variant="h6" 
                            fontWeight="700" 
                            color={isTop3 ? "#5C6BC0" : "text.primary"}
                            sx={{ minWidth: "fit-content" }}
                          >
                            {share.percentageValue.toFixed(2)}%
                          </Typography>
                        </Stack>
                        
                        <Box sx={{ mt: 1.5 }}>
                          <Box
                            sx={{
                              height: 4,
                              bgcolor: "#E0E0E0",
                              borderRadius: 2,
                              overflow: "hidden",
                            }}
                          >
                            <Box
                              sx={{
                                height: "100%",
                                width: `${Math.min(share.percentageValue, 100)}%`,
                                bgcolor: isTop3 ? "#5C6BC0" : "#9E9E9E",
                                borderRadius: 2,
                                transition: "width 0.5s cubic-bezier(0.4, 0, 0.2, 1)",
                              }}
                            />
                          </Box>
                        </Box>
                      </Box>
                    );
                  })}
              </Stack>
            </Box>
          </Paper>
        </Stack>
      </Container>
    </Box>
  );
};

export default PortfolioStatisticsMap;