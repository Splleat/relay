import { Modal } from '../../ui/Modal.tsx';
import { CreateSpaceChannelForm } from './CreateSpaceChannelForm.tsx';
import { useModal } from '../../hooks/useModal.ts';

export function CreateSpaceChannelButton({
    spaceId,
    onSuccess,
}: {
    spaceId: string;
    onSuccess: () => void;
}) {
    const { isOpen, open, close } = useModal();

    const handleCreate = () => {
        onSuccess();
        close();
    };

    return (
        <div>
            <button
                onClick={open}
                title="채널 생성"
                aria-label="채널 생성"
                className="flex size-6 cursor-pointer items-center justify-center rounded-md text-base text-muted transition-colors hover:bg-hover hover:text-fg"
            >
                +
            </button>
            <Modal open={isOpen} onClose={close}>
                <CreateSpaceChannelForm
                    onCreate={handleCreate}
                    spaceId={spaceId}
                />
            </Modal>
        </div>
    );
}
