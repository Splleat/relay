import type { ReactNode } from 'react';

export function StatusScreen({ children }: { children: ReactNode }) {
    return (
        <div className="flex min-h-dvh items-center justify-center text-sm text-muted">
            {children}
        </div>
    );
}
