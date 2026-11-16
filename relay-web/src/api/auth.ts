import { clearCsrfToken, fetchApi } from './client.ts';

export interface SignUpRequest {
    email: string;
    name: string;
    password: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export async function signUp(request: SignUpRequest) {
    await fetchApi('/users', 'POST', request);
}

export async function login(request: LoginRequest) {
    await fetchApi('/auth/login', 'POST', request);

    clearCsrfToken();
}

export async function logout() {
    await fetchApi('/auth/logout', 'POST');

    clearCsrfToken();
}
