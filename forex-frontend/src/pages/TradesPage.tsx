import React, { useState } from 'react';
import GlassCard from '../components/common/GlassCard';
import { useTrading } from '../hooks/useTrading';
import { useExchangeRates } from '../hooks/useExchangeRates';

const TradesPage: React.FC = () => {
  const { trades, createTrade, isCreating } = useTrading();
  const { rates } = useExchangeRates();
  const [formData, setFormData] = useState({
    type: 'BUY' as 'BUY' | 'SELL',
    pair: 'EUR/USD',
    amount: '',
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createTrade({
      type: formData.type,
      pair: formData.pair,
      amount: parseFloat(formData.amount),
    });
    setFormData({ ...formData, amount: '' });
  };

  const pairs = rates?.map((r) => r.pair) || ['EUR/USD', 'GBP/USD', 'USD/JPY', 'AUD/USD'];

  return (
    <div className="space-y-6">
      <h1 className="font-headline text-2xl font-bold text-text-primary">Trading</h1>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <GlassCard>
          <h2 className="font-headline text-lg font-bold text-text-primary mb-4">New Trade</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="flex gap-2">
              <button
                type="button"
                onClick={() => setFormData({ ...formData, type: 'BUY' })}
                className={`flex-1 py-3 text-sm font-bold transition-colors ${
                  formData.type === 'BUY'
                    ? 'bg-accent-green text-bg-primary'
                    : 'bg-bg-surface text-text-secondary'
                }`}
              >
                BUY
              </button>
              <button
                type="button"
                onClick={() => setFormData({ ...formData, type: 'SELL' })}
                className={`flex-1 py-3 text-sm font-bold transition-colors ${
                  formData.type === 'SELL'
                    ? 'bg-accent-red text-bg-primary'
                    : 'bg-bg-surface text-text-secondary'
                }`}
              >
                SELL
              </button>
            </div>
            <div>
              <label className="block text-text-secondary text-sm mb-2">Currency Pair</label>
              <select
                value={formData.pair}
                onChange={(e) => setFormData({ ...formData, pair: e.target.value })}
                className="input-flat"
              >
                {pairs.map((pair) => (
                  <option key={pair} value={pair} className="bg-bg-surface">
                    {pair}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-text-secondary text-sm mb-2">Amount</label>
              <input
                type="number"
                value={formData.amount}
                onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                className="input-flat"
                placeholder="Enter amount"
                step="0.01"
              />
            </div>
            <button
              type="submit"
              disabled={isCreating}
              className={`w-full py-3 font-headline font-bold transition-colors disabled:opacity-50 ${
                formData.type === 'BUY'
                  ? 'bg-accent-green text-bg-primary hover:opacity-90'
                  : 'bg-accent-red text-bg-primary hover:opacity-90'
              }`}
            >
              {isCreating ? 'Processing...' : `${formData.type} ${formData.pair}`}
            </button>
          </form>
        </GlassCard>

        <GlassCard>
          <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Live Rates</h2>
          <div className="space-y-3">
            {rates?.map((rate) => (
              <div key={rate.pair} className="flex items-center justify-between py-2 border-b border-border-subtle">
                <span className="text-text-primary font-bold">{rate.pair}</span>
                <div className="text-right">
                  <p className="text-text-primary font-mono">{rate.bid.toFixed(5)}</p>
                  <p className="text-text-muted text-xs">Ask: {rate.ask.toFixed(5)}</p>
                </div>
              </div>
            ))}
            {(!rates || rates.length === 0) && (
              <p className="text-text-muted">Loading rates...</p>
            )}
          </div>
        </GlassCard>
      </div>

      <GlassCard>
        <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Recent Trades</h2>
        {trades && trades.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-border-light">
                  <th className="text-left text-text-muted text-xs font-bold py-3">TYPE</th>
                  <th className="text-left text-text-muted text-xs font-bold py-3">PAIR</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">AMOUNT</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">RATE</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">STATUS</th>
                  <th className="text-right text-text-muted text-xs font-bold py-3">TIME</th>
                </tr>
              </thead>
              <tbody>
                {trades.map((trade) => (
                  <tr key={trade.id} className="border-b border-border-subtle">
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
                      {new Date(trade.createdAt).toLocaleString()}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <p className="text-text-muted text-sm">No trades yet</p>
        )}
      </GlassCard>
    </div>
  );
};

export default TradesPage;