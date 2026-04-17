import React, { useEffect } from 'react';
import { useUIStore } from '../../stores/uiStore';

const Notification: React.FC = () => {
  const { notifications, removeNotification } = useUIStore();

  useEffect(() => {
    if (notifications.length > 0) {
      const timer = setTimeout(() => {
        removeNotification(notifications[0].id);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [notifications, removeNotification]);

  if (notifications.length === 0) return null;

  return (
    <div className="fixed top-4 right-4 z-50 flex flex-col gap-2">
      {notifications.map((notification) => (
        <div
          key={notification.id}
          className={`px-4 py-3 text-sm border ${
            notification.type === 'success'
              ? 'status-badge success'
              : notification.type === 'error'
              ? 'status-badge error'
              : 'status-badge pending'
          }`}
        >
          {notification.message}
        </div>
      ))}
    </div>
  );
};

export default Notification;