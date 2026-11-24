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
            <button
                onClick={open}
                title="스페이스 생성"
                aria-label="스페이스 생성"
                className="flex size-11 cursor-pointer items-center justify-center rounded-2xl border border-dashed border-rail-fg/40 text-xl text-rail-fg transition-all hover:rounded-xl hover:border-solid hover:border-accent hover:text-accent"
            >
                +
            </button>
            <Modal open={isOpen} onClose={close}>
                <CreateSpaceForm onCreate={handleCreate} />
            </Modal>
        </div>
    );
}
