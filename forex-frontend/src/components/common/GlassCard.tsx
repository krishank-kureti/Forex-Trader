import React from 'react';

interface GlassCardProps {
  children: React.ReactNode;
  className?: string;
}

const GlassCard: React.FC<GlassCardProps> = ({ children, className = '' }) => {
  return (
    <div className={`card-flat p-6 ${className}`}>
      {children}
    </div>
  );
};

export default GlassCard;