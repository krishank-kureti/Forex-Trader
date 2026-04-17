import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import walletApi from '../api/wallet';
import { useUIStore } from '../stores/uiStore';
import type { WalletRequest } from '../types';
interface DepositVariables {
 amount: number;
 password: string;
}
interface WithdrawVariables {
 amount: number;
 password: string;
}
export const useWallet = () => {
 const queryClient = useQueryClient();
 const addNotification = useUIStore((state) => state.addNotification);
 const balanceQuery = useQuery({
 queryKey: ['wallet', 'balance'],
 queryFn: walletApi.getBalance,
 staleTime: 30000,
 });
 const transactionsQuery = useQuery({
 queryKey: ['wallet', 'transactions'],
 queryFn: walletApi.getTransactions,
 staleTime: 60000,
 });
 const depositMutation = useMutation({
 mutationFn: (variables: DepositVariables) => {
 const request: WalletRequest = {
 amount: variables.amount,
 password: variables.password,
 };
 return walletApi.deposit(request);
 },
 onSuccess: () => {
 queryClient.invalidateQueries({ queryKey: ['wallet'] });
 addNotification('success', 'Deposit successful!');
 },
 onError: (error: Error) => {
 addNotification('error', error.message || 'Deposit failed');
 },
 });
 const withdrawMutation = useMutation({
 mutationFn: (variables: WithdrawVariables) => {
 const request: WalletRequest = {
 amount: variables.amount,
 password: variables.password,
 };
 return walletApi.withdraw(request);
 },
 onSuccess: () => {
 queryClient.invalidateQueries({ queryKey: ['wallet'] });
 addNotification('success', 'Withdrawal successful!');
 },
 onError: (error: Error) => {
 addNotification('error', error.message || 'Withdrawal failed');
 },
 });
 return {
 balance: balanceQuery.data ?? 0,
 isLoadingBalance: balanceQuery.isLoading,
 transactions: transactionsQuery.data ?? [],
 isLoadingTransactions: transactionsQuery.isLoading,
 isDepositing: depositMutation.isPending,
 isWithdrawing: withdrawMutation.isPending,
 deposit: depositMutation.mutate,
 withdraw: withdrawMutation.mutate,
 refetchBalance: balanceQuery.refetch,
 refetchTransactions: transactionsQuery.refetch,
 };
};
export default useWallet;
