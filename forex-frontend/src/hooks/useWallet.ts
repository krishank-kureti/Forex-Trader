import { useQuery } from '@tanstack/react-query';
import { walletApi } from '../api/wallet';

export const useWallet = () => {
  const balanceQuery = useQuery({
    queryKey: ['wallet', 'balance'],
    queryFn: walletApi.getBalance,
  });

  const transactionsQuery = useQuery({
    queryKey: ['wallet', 'transactions'],
    queryFn: walletApi.getTransactions,
  });

  return {
    balance: balanceQuery.data,
    transactions: transactionsQuery.data,
    isLoading: balanceQuery.isLoading,
    isError: balanceQuery.isError,
  };
};