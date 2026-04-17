import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Link } from 'react-router-dom';
import { authApi } from '../api/auth';
import { useAuthStore } from '../stores/authStore';

const registerSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters'),
  email: z.string().email('Invalid email address'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ['confirmPassword'],
});

type RegisterForm = z.infer<typeof registerSchema>;

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const { setAuth } = useAuthStore();
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<RegisterForm>({
    resolver: zodResolver(registerSchema),
  });

  const onSubmit = async (data: RegisterForm) => {
    try {
      const response = await authApi.register({
        name: data.name,
        email: data.email,
        password: data.password,
      });
      setAuth(response.user, response.token);
      navigate('/');
    } catch (error: any) {
      setError('root', { message: error.response?.data?.message || 'Registration failed' });
    }
  };

  return (
    <div className="min-h-screen bg-bg-secondary flex items-center justify-center blueprint-grid">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <h1 className="font-headline text-3xl font-bold text-text-primary">FOREX TRADER</h1>
          <p className="text-text-muted mt-2">Create your account</p>
        </div>
        <div className="card-flat p-8">
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div>
              <label className="block text-text-secondary text-sm mb-2">Name</label>
              <input
                {...register('name')}
                type="text"
                className="input-flat"
                placeholder="Enter your name"
              />
              {errors.name && <p className="text-accent-red text-xs mt-1">{errors.name.message}</p>}
            </div>
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
                placeholder="Create a password"
              />
              {errors.password && <p className="text-accent-red text-xs mt-1">{errors.password.message}</p>}
            </div>
            <div>
              <label className="block text-text-secondary text-sm mb-2">Confirm Password</label>
              <input
                {...register('confirmPassword')}
                type="password"
                className="input-flat"
                placeholder="Confirm your password"
              />
              {errors.confirmPassword && <p className="text-accent-red text-xs mt-1">{errors.confirmPassword.message}</p>}
            </div>
            {errors.root && <p className="text-accent-red text-sm">{errors.root.message}</p>}
            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full bg-accent-green text-bg-primary font-headline font-bold py-3 hover:opacity-90 transition-opacity disabled:opacity-50"
            >
              {isSubmitting ? 'Creating account...' : 'CREATE ACCOUNT'}
            </button>
          </form>
          <p className="text-center text-text-muted text-sm mt-6">
            Already have an account?{' '}
            <Link to="/login" className="text-accent-green hover:underline">
              Sign in
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;