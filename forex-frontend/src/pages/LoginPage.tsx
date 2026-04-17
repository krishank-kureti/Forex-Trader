import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Link } from 'react-router-dom';
import { authApi } from '../api/auth';
import { useAuthStore } from '../stores/authStore';

const loginSchema = z.object({
  email: z.string().email('Invalid email address'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
});

type LoginForm = z.infer<typeof loginSchema>;

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const { setAuth } = useAuthStore();
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<LoginForm>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = async (data: LoginForm) => {
    try {
      const response = await authApi.login(data);
      setAuth(response.user, response.token);
      navigate('/');
    } catch (error: any) {
      setError('root', { message: error.response?.data?.message || 'Login failed' });
    }
  };

  return (
    <div className="min-h-screen bg-bg-secondary flex items-center justify-center blueprint-grid">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <h1 className="font-headline text-3xl font-bold text-text-primary">FOREX TRADER</h1>
          <p className="text-text-muted mt-2">Sign in to your account</p>
        </div>
        <div className="card-flat p-8">
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div>
              <label className="block text-text-secondary text-sm mb-2">Email</label>
              <input
                {...register('email')}
                type="email"
                className="input-flat"
                placeholder="Enter your email"
              />
              {errors.email && <p className="text-accent-red text-xs mt-1">{errors.email.message}</p>}
            </div>
            <div>
              <label className="block text-text-secondary text-sm mb-2">Password</label>
              <input
                {...register('password')}
                type="password"
                className="input-flat"
                placeholder="Enter your password"
              />
              {errors.password && <p className="text-accent-red text-xs mt-1">{errors.password.message}</p>}
            </div>
            {errors.root && <p className="text-accent-red text-sm">{errors.root.message}</p>}
            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full bg-accent-green text-bg-primary font-headline font-bold py-3 hover:opacity-90 transition-opacity disabled:opacity-50"
            >
              {isSubmitting ? 'Signing in...' : 'SIGN IN'}
            </button>
          </form>
          <p className="text-center text-text-muted text-sm mt-6">
            Don't have an account?{' '}
            <Link to="/register" className="text-accent-green hover:underline">
              Register
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;