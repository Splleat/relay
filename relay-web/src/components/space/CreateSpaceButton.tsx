import { useState } from 'react';
import { Modal } from '../../ui/Modal.tsx';
import { CreateSpaceForm } from './CreateSpaceForm.tsx';

export function CreateSpaceButton({ onSuccess }: { onSuccess: () => void }) {
    const [isOpen, setIsOpen] = useState<boolean>(false);

    function handleClose() {
        setIsOpen(false);
    }

    function handleOpen() {
        setIsOpen(true);
    }

    function handleCreate() {
        onSuccess();
        handleClose();
    }

    return (
        <div>
            <button onClick={handleOpen}>스페이스 생성</button>
            <Modal open={isOpen} onClose={handleClose}>
                <CreateSpaceForm onCreate={handleCreate} />
            </Modal>
        </div>
    );
}
