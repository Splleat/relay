import { useParams } from 'react-router';

export function useRequiredParam(name: string): string {
    const value = useParams()[name];

    if (value === undefined) {
        throw new Error(`라우트 파라미터가 undefined: ${name}`);
    }

    return value;
}
