import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { tradesApi } from '../api/trades';
import { TradeRequest } from '../types';

export const useTrading = () => {
  const queryClient = useQueryClient();

  const tradesQuery = useQuery({
    queryKey: ['trades'],
    queryFn: tradesApi.getTrades,
  });

  const createTradeMutation = useMutation({
    mutationFn: (data: TradeRequest) => tradesApi.createTrade(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['trades'] });
    },
  });

  return {
    trades: tradesQuery.data,
    isLoading: tradesQuery.isLoading,
    createTrade: createTradeMutation.mutate,
    isCreating: createTradeMutation.isPending,
  };
};