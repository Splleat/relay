import { Modal } from '../../ui/Modal.tsx';
import { CreateSpaceForm } from './CreateSpaceForm.tsx';
import { useModal } from '../../hooks/useModal.ts';

export function CreateSpaceButton({ onSuccess }: { onSuccess: () => void }) {
    const { isOpen, open, close } = useModal();

    const handleCreate = () => {
        onSuccess();
        close();
    };

    return (
        <div>
            <button onClick={open}>스페이스 생성</button>
            <Modal open={isOpen} onClose={close}>
                <CreateSpaceForm onCreate={handleCreate} />
            </Modal>
        </div>
    );
}
