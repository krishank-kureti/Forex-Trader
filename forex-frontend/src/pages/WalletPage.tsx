import React, { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useAuthStore } from '../stores/authStore';
import { useWallet } from '../hooks/useWallet';
const amountSchema = z.object({
 amount: z.number().min(1, 'Amount must be at least 1'),
 password: z.string().min(1, 'Password is required'),
});
type AmountFormData = z.infer<typeof amountSchema>;
type FilterField = 'all' | 'timestamp' | 'executionType' | 'amountDelta' | 'statusCode';
type SortOrder = 'asc' | 'desc';
export const WalletPage: React.FC = () => {
 const navigate = useNavigate();
 const { isAuthenticated } = useAuthStore();
 const {
 balance,
 transactions,
 isLoadingBalance,
 isLoadingTransactions,
 isDepositing,
 isWithdrawing,
 deposit,
 withdraw
 } = useWallet();

 const [activeTab, setActiveTab] = useState<'deposit' | 'withdraw'>('deposit');
 const [showFilter, setShowFilter] = useState(false);
 const [filterField, setFilterField] = useState<FilterField>('all');
 const [filterValue, setFilterValue] = useState('');
 const [sortOrder, setSortOrder] = useState<SortOrder>('desc');

 const { register, handleSubmit, reset, formState: { errors } } = useForm<AmountFormData>({
 resolver: zodResolver(amountSchema),
 });
 if (!isAuthenticated) {
 navigate('/login');
 return null;
 }
 const onSubmit = (data: AmountFormData) => {
 if (activeTab === 'deposit') {
 deposit({ amount: data.amount, password: data.password }, {
 onSuccess: () => {
 reset();
 },
 onError: () => {
 // Handle error - password incorrect
 }
 });
 } else {
 withdraw({ amount: data.amount, password: data.password }, {
 onSuccess: () => {
 reset();
 },
 onError: () => {
 // Handle error - password incorrect
 }
 });
 }
 };
 const formatCurrency = (value: number) => {
 return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value);
 };
 const formatDate = (timestamp: string) => {
 return new Date(timestamp).toLocaleString('en-US', {
 year: 'numeric',
 month: '2-digit',
 day: '2-digit',
 hour: '2-digit',
 minute: '2-digit',
 second: '2-digit',
 }).replace(',', '');
 };
 const filteredTransactions = useMemo(() => {
 let filtered = [...transactions];

 if (filterField !== 'all' && filterValue) {
 filtered = filtered.filter(tx => {
 switch (filterField) {
 case 'timestamp':
 return formatDate(tx.timestamp).toLowerCase().includes(filterValue.toLowerCase());
 case 'executionType':
 return tx.type.toLowerCase().includes(filterValue.toLowerCase());
 case 'amountDelta':
 return tx.amount.toString().includes(filterValue);
 case 'statusCode':
 return tx.status.toLowerCase().includes(filterValue.toLowerCase());
 default:
 return true;
 }
 });
 }

 filtered.sort((a, b) => {
 const comparison = new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
 return sortOrder === 'asc' ? comparison : -comparison;
 });

 return filtered;
 }, [transactions, filterField, filterValue, sortOrder]);
 const exportToCSV = () => {
 const headers = ['Timestamp_UTC', 'Execution_Type', 'Reference_ID', 'Amount_Delta', 'Status_Code'];
 const rows = filteredTransactions.map(tx => [
 formatDate(tx.timestamp),
 tx.type === 'DEPOSIT' ? 'External_Deposit' : 'Withdrawal_Request',
 `TX-${tx.id.toString().padStart(6, '0')}-XX`,
 `${tx.type === 'DEPOSIT' ? '+' : '-'}${tx.amount}`,
 tx.status
 ]);

 const csvContent = [
 headers.join(','),
 ...rows.map(row => row.join(','))
 ].join('\n');

 const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
 const link = document.createElement('a');
 link.href = URL.createObjectURL(blob);
 link.download = `transaction_ledger_${new Date().toISOString().split('T')[0]}.csv`;
 link.click();
 };
 const clearFilter = () => {
 setFilterField('all');
 setFilterValue('');
 };
 return (
 <div className="space-y-8 max-w-6xl mx-auto">
 <div className="grid grid-cols-12 gap-8">
 <div className="col-span-12 lg:col-span-8 border border-white/10 bg-[#1f1f1f] p-8 relative overflow-hidden">
 <div className="absolute top-0 right-0 p-4 border-l border-b border-white/10 opacity-20">
 <span className="material-symbols-outlined text-4xl">hub</span>
 </div>
 <div className="space-y-1">
 <p className="font-headline uppercase tracking-[0.2em] text-xs text-white/40">Total_Equity_USd</p>
 <h2 className="text-6xl font-headline font-bold tracking-tighter text-white">
 {isLoadingBalance ? '---' : (
 <>
 {formatCurrency(balance).split('.')[0]}.<span className="text-white/40">{formatCurrency(balance).split('.')[1] ||
 </>
 )}
 </h2>
 </div>
 <div className="mt-12 flex flex-wrap gap-4">
 <button
 onClick={() => setActiveTab('deposit')}
 className="px-10 py-3 border border-[#4ae183] text-[#4ae183] font-headline text-xs font-black tracking-[0.2em] hover:bg
 >
 DEPOSIT_FUNDS
 </button>
 <button
 onClick={() => setActiveTab('withdraw')}
 className="px-10 py-3 border border-white/20 text-white/60 font-headline text-xs font-black tracking-[0.2em] hover:bord
 >
 WITHDRAW_ASSETS
 </button>
 </div>
 <div className="mt-8 grid grid-cols-3 border-t border-white/10 pt-8 gap-4">
 <div>
 <p className="text-[10px] text-white/30 uppercase tracking-widest font-headline">Margin_Available</p>
 <p className="text-lg font-headline font-medium text-white">{isLoadingBalance ? '---' : formatCurrency(balance * 0.5)}<
 </div>
 <div>
 <p className="text-[10px] text-white/30 uppercase tracking-widest font-headline">Unrealized_P&L</p>
 <p className="text-lg font-headline font-medium text-[#4ae183]">+$0.00</p>
 </div>
 <div>
 <p className="text-[10px] text-white/30 uppercase tracking-widest font-headline">Wallet_Address</p>
 <p className="text-lg font-headline font-medium text-white/60 truncate">0x4F...E821</p>
 </div>
 </div>
 </div>
 <div className="col-span-12 lg:col-span-4 border border-white/10 bg-[#1f1f1f] p-6 space-y-6">
 <h3 className="font-headline uppercase tracking-[0.1em] text-sm font-bold text-white border-b border-white/10 pb-4">
 {activeTab === 'deposit' ? 'Deposit_Funds' : 'Withdraw_Assets'}
 </h3>

 <div className="flex border border-white/10">
 <button
 onClick={() => setActiveTab('deposit')}
 className={`flex-1 py-3 font-headline text-xs font-bold tracking-[0.1em] uppercase cursor-pointer transition-all ${
 activeTab === 'deposit'
 ? 'bg-[#4ae183]/10 text-[#4ae183] border-r border-white/10'
 : 'text-white/40 hover:text-white/70'
 }`}
 >
 Deposit
 </button>
 <button
 onClick={() => setActiveTab('withdraw')}
 className={`flex-1 py-3 font-headline text-xs font-bold tracking-[0.1em] uppercase cursor-pointer transition-all ${
 activeTab === 'withdraw'
 ? 'bg-[#fa5a48]/10 text-[#fa5a48]'
 : 'text-white/40 hover:text-white/70'
 }`}
 >
 Withdraw
 </button>
 </div>
 <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
 <div>
 <label className="block text-[10px] text-white/40 uppercase tracking-widest font-headline mb-2">Amount_USD</label>
 <input
 type="number"
 step="0.01"
 {...register('amount', { valueAsNumber: true })}
 className="input-flat font-mono"
 placeholder="0.00"
 />
 {errors.amount && (
 <p className="mt-2 text-[10px] text-[#fa5a48] font-mono">{errors.amount.message}</p>
 )}
 </div>

 <div>
 <label className="block text-[10px] text-white/40 uppercase tracking-widest font-headline mb-2">Confirm_Password</label
 <input
 type="password"
 {...register('password')}
 className="input-flat font-mono"
 placeholder="••••••••"
 />
 {errors.password && (
 <p className="mt-2 text-[10px] text-[#fa5a48] font-mono">{errors.password.message}</p>
 )}
 </div>

 <button
 type="submit"
 className={`w-full py-4 font-headline text-xs font-bold tracking-[0.2em] uppercase transition-all cursor-pointer ${
 activeTab === 'deposit'
 ? 'border border-[#4ae183] text-[#4ae183] hover:bg-[#4ae183]/10'
 : 'border border-[#fa5a48] text-[#fa5a48] hover:bg-[#fa5a48]/10'
 }`}
 disabled={isDepositing || isWithdrawing}
 >
 {isDepositing || isWithdrawing ? 'PROCESSING...' : activeTab === 'deposit' ? 'DEPOSIT_FUNDS' : 'WITHDRAW_ASSETS'}
 </button>
 </form>
 </div>
 </div>
 <div className="border border-white/10 bg-[#1f1f1f]">
 <div className="flex justify-between items-center p-6 border-b border-white/10">
 <div className="flex items-center gap-3">
 <span className="material-symbols-outlined text-[#4ae183]">history</span>
 <h3 className="font-headline uppercase tracking-[0.1em] text-sm font-bold text-white">Transaction_Ledger_V2</h3>
 </div>
 <div className="flex gap-2">
 <button
 onClick={exportToCSV}
 className="px-4 py-1.5 border border-white/10 text-[10px] font-headline uppercase tracking-widest text-white/40 hover:t
 >
 Export_CSV
 </button>
 <button
 onClick={() => setShowFilter(!showFilter)}
 className={`px-4 py-1.5 border text-[10px] font-headline uppercase tracking-widest transition-all cursor-pointer ${
 showFilter
 ? 'border-[#4ae183] text-[#4ae183] bg-[#4ae183]/10'
 : 'border-white/10 text-white/40 hover:text-white hover:border-white/30'
 }`}
 >
 Filter
 </button>
 </div>
 </div>

 {showFilter && (
 <div className="p-4 border-b border-white/10 bg-[#1a1a1a] flex flex-wrap items-center gap-4">
 <div className="flex items-center gap-2">
 <span className="text-[10px] text-white/40 font-headline uppercase tracking-widest">Field:</span>
 <select
 value={filterField}
 onChange={(e) => setFilterField(e.target.value as FilterField)}
 className="input-flat text-xs font-mono py-1.5"
 >
 <option value="all">All Fields</option>
 <option value="timestamp">Timestamp_UTC</option>
 <option value="executionType">Execution_Type</option>
 <option value="amountDelta">Amount_Delta</option>
 <option value="statusCode">Status_Code</option>
 </select>
 </div>

 {filterField !== 'all' && (
 <>
 <div className="flex items-center gap-2">
 <span className="text-[10px] text-white/40 font-headline uppercase tracking-widest">Value:</span>
 {filterField === 'executionType' ? (
 <select
 value={filterValue}
 onChange={(e) => setFilterValue(e.target.value)}
 className="input-flat text-xs font-mono py-1.5"
 >
 <option value="">All Types</option>
 <option value="DEPOSIT">External_Deposit</option>
 <option value="WITHDRAWAL">Withdrawal_Request</option>
 </select>
 ) : filterField === 'statusCode' ? (
 <select
 value={filterValue}
 onChange={(e) => setFilterValue(e.target.value)}
 className="input-flat text-xs font-mono py-1.5"
 >
 <option value="">All Status</option>
 <option value="SUCCESS">Success</option>
 <option value="FAILED">Failed</option>
 <option value="PENDING">Pending</option>
 </select>
 ) : (
 <input
 type="text"
 value={filterValue}
 onChange={(e) => setFilterValue(e.target.value)}
 className="input-flat text-xs font-mono py-1.5 w-40"
 placeholder="Filter value..."
 />
 )}
 </div>

 <div className="flex items-center gap-2">
 <span className="text-[10px] text-white/40 font-headline uppercase tracking-widest">Sort:</span>
 <select
 value={sortOrder}
 onChange={(e) => setSortOrder(e.target.value as SortOrder)}
 className="input-flat text-xs font-mono py-1.5"
 >
 <option value="desc">Newest First</option>
 <option value="asc">Oldest First</option>
 </select>
 </div>

 <button
 onClick={clearFilter}
 className="px-3 py-1.5 text-[10px] font-headline uppercase tracking-widest text-white/40 hover:text-white transitio
 >
 Clear
 </button>
 </>
 )}
 </div>
 )}

 <div className="overflow-x-auto">
 <table className="w-full text-left border-collapse">
 <thead>
 <tr className="border-b border-white/5 bg-white/[0.02]">
 <th className="p-6 text-[10px] font-headline uppercase tracking-[0.2em] text-white/30">Timestamp_UTC</th>
 <th className="p-6 text-[10px] font-headline uppercase tracking-[0.2em] text-white/30">Execution_Type</th>
 <th className="p-6 text-[10px] font-headline uppercase tracking-[0.2em] text-white/30">Reference_ID</th>
 <th className="p-6 text-[10px] font-headline uppercase tracking-[0.2em] text-white/30">Amount_Delta</th>
 <th className="p-6 text-[10px] font-headline uppercase tracking-[0.2em] text-white/30">Status_Code</th>
 </tr>
 </thead>
 <tbody className="divide-y divide-white/5">
 {isLoadingTransactions ? (
 <tr>
 <td colSpan={5} className="p-6 text-center text-white/40 font-mono">Loading transaction data...</td>
 </tr>
 ) : filteredTransactions.length === 0 ? (
 <tr>
 <td colSpan={5} className="p-6 text-center text-white/40 font-mono">
 {filterField !== 'all' && filterValue ? 'No matching transactions' : 'No transactions in ledger'}
 </td>
 </tr>
 ) : (
 filteredTransactions.map((tx) => (
 <tr key={tx.id} className="hover:bg-white/[0.03] transition-colors cursor-pointer">
 <td className="p-6 text-xs text-white/60 font-mono">{formatDate(tx.timestamp)}</td>
 <td className="p-6">
 <div className="flex flex-col">
 <span className="text-xs font-bold text-white uppercase font-headline">
 {tx.type === 'DEPOSIT' ? 'External_Deposit' : 'Withdrawal_Request'}
 </span>
 <span className="text-[9px] text-white/30 uppercase tracking-tighter">via Internal System</span>
 </div>
 </td>
 <td className="p-6 text-xs font-mono text-white/40">TX-{tx.id.toString().padStart(6, '0')}-XX</td>
 <td className="p-6 text-xs font-headline font-bold">
 <span className={tx.type === 'DEPOSIT' ? 'text-[#4ae183]' : 'text-[#fa5a48]'}>
 {tx.type === 'DEPOSIT' ? '+' : '-'}{formatCurrency(tx.amount)}
 </span>
 </td>
 <td className="p-6">
 <span className={`status-badge ${
 tx.status === 'SUCCESS' ? 'success' :
 tx.status === 'FAILED' ? 'error' : 'pending'
 }`}>
 {tx.status}
 </span>
 {tx.message && (
 <span className="block text-[9px] text-white/40 mt-1">{tx.message}</span>
 )}
 </td>
 </tr>
 ))
 )}
 </tbody>
 </table>
 </div>
 <div className="p-6 border-t border-white/5 bg-white/[0.01] flex justify-between items-center">
 <p className="text-[10px] text-white/20 uppercase tracking-widest font-headline">
 Showing {filteredTransactions.length} of {transactions.length} entries
 </p>
 </div>
 </div>
 </div>
 );
};
export default WalletPage;