const BASE_URL = '/api/v1';

interface ApiErrorResponse {
    code: string;
    message: string;
    errors: FieldErrorDetail[];
}

interface FieldErrorDetail {
    field: string;
    message: string;
}

export class ApiError extends Error {
    status: number;
    code: string;

    constructor(status: number, code: string, message: string) {
        super(message);
        this.status = status;
        this.code = code;
    }
}

export async function fetchApi<T>(
    endpoint: string,
    method: string = 'GET',
    body?: unknown,
) {
    const url = `${BASE_URL}${endpoint}`;

    const reqBody = body ? JSON.stringify(body) : null;
    const headers = new Headers();

    if (body) headers.set('Content-Type', 'application/json');

    const res = await fetch(url, {
        method,
        body: reqBody,
        headers,
    });

    const resBody = await res.text();

    if (res.ok) {
        if (!resBody) {
            return undefined as T;
        }

        return JSON.parse(resBody) as T;
    }

    const { code, message }: ApiErrorResponse = JSON.parse(resBody);

    throw new ApiError(res.status, code, message);
}
