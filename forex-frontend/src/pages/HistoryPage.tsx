import React from 'react';
import GlassCard from '../components/common/GlassCard';
import { useTrading } from '../hooks/useTrading';

const HistoryPage: React.FC = () => {
  const { trades, isLoading } = useTrading();

  const settledTrades = trades?.filter((t) => t.status === 'COMPLETED' || t.status === 'FAILED') || [];

  return (
    <div className="space-y-6">
      <h1 className="font-headline text-2xl font-bold text-text-primary">Trade History</h1>

      <GlassCard>
        {isLoading ? (
          <p className="text-text-muted">Loading history...</p>
        ) : settledTrades.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-border-light">
                  <th className="text-left text-text-muted text-xs font-bold py-3">ID</th>
                  <th className="text-left text-text-muted text-xs font-bold py-3">TYPE</th>
                  <th className="text-left text-text-muted text-xs font-bold py-3">PAIR</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">AMOUNT</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">RATE</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">STATUS</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">SETTLED</th>
                </tr>
              </thead>
              <tbody>
                {settledTrades.map((trade) => (
                  <tr key={trade.id} className="border-b border-border-subtle">
                    <td className="py-3 text-text-muted text-xs font-mono">{trade.id.slice(0, 8)}</td>
                    <td className={`py-3 text-sm font-bold ${trade.type === 'BUY' ? 'text-accent-green' : 'text-accent-red'}`}>
                      {trade.type}
                    </td>
                    <td className="py-3 text-text-secondary text-sm">{trade.pair}</td>
                    <td className="py-3 text-text-primary text-sm text-right font-mono">${trade.amount.toFixed(2)}</td>
                    <td className="py-3 text-text-secondary text-sm text-right font-mono">{trade.rate.toFixed(5)}</td>
                    <td className="py-3 text-right">
                      <span className={`status-badge ${trade.status.toLowerCase()}`}>
                        {trade.status}
                      </span>
                    </td>
                    <td className="py-3 text-text-muted text-xs text-right">
                      {trade.settledAt ? new Date(trade.settledAt).toLocaleString() : '-'}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <p className="text-text-muted text-sm">No settled trades yet</p>
        )}
      </GlassCard>
    </div>
  );
};

export default HistoryPage;