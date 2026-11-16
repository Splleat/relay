export interface ApiErrorResponse {
    code: string;
    message: string;
    errors: FieldErrorDetail[];
}

export interface FieldErrorDetail {
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

export function getErrorMessage(error: unknown): string {
    if (error instanceof ApiError) {
        return error.message;
    }

    console.error(error);

    return '알 수 없는 에러';
}
