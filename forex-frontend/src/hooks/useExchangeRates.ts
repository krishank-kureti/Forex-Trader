import { useQuery } from '@tanstack/react-query';
import { exchangeRateApi } from '../api/exchange-rate';

export const useExchangeRates = () => {
  const ratesQuery = useQuery({
    queryKey: ['exchange-rates'],
    queryFn: exchangeRateApi.getRates,
    refetchInterval: 10000,
  });

  return {
    rates: ratesQuery.data,
    isLoading: ratesQuery.isLoading,
  };
};