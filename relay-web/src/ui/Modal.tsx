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
        <dialog ref={ref} onClose={onClose} onClick={handleDialogClick}>
            {children}
        </dialog>
    );
}
