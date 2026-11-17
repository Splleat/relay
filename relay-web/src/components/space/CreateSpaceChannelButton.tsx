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
            <button onClick={open}>채널 생성</button>
            <Modal open={isOpen} onClose={close}>
                <CreateSpaceChannelForm
                    onCreate={handleCreate}
                    spaceId={spaceId}
                />
            </Modal>
        </div>
    );
}
