import { type ReactNode, type MouseEvent, useEffect, useRef } from 'react';

export function Modal({
    open,
    children,
    onClose,
}: {
    open: boolean;
    children: ReactNode;
    onClose: () => void;
}) {
    const ref = useRef<HTMLDialogElement>(null);

    useEffect(() => {
        const dialog = ref.current;

        if (!dialog) return;
        if (open && !dialog.open) dialog.showModal();
        if (!open && dialog.open) dialog.close();
    }, [open]);

    function handleDialogClick(e: MouseEvent<HTMLDialogElement>) {
        if (e.target === ref.current) {
            onClose();
        }
    }

    return (
        <dialog
            ref={ref}
            onClose={onClose}
            onClick={handleDialogClick}
            className="m-auto w-full max-w-sm rounded-2xl border border-border bg-surface p-0 text-fg shadow-xl backdrop:bg-black/40"
        >
            <div className="p-6">{children}</div>
        </dialog>
    );
}
