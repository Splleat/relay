import { fetchApi } from './client.ts';

export interface UserResponse {
    name: string;
    email: string;
    imageUrl: string | null;
    statusMessage: string | null;
}

export async function getMe(): Promise<UserResponse> {
    return await fetchApi<UserResponse>('/users/me');
}
