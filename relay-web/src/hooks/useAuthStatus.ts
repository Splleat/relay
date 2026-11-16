import { useEffect, useState } from 'react';
import { getMe } from '../api/user.ts';
import { ApiError } from '../api/errors.ts';

export type AuthStatus =
    'loading' | 'authenticated' | 'unauthenticated' | 'error';

export function useAuthStatus(): AuthStatus {
    const [status, setStatus] = useState<AuthStatus>('loading');

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                await getMe();

                setStatus('authenticated');
            } catch (error) {
                if (error instanceof ApiError && error.status === 401) {
                    setStatus('unauthenticated');
                } else {
                    console.error(error);

                    setStatus('error');
                }
            }
        };

        void fetchProfile();
    }, []);

    return status;
}
