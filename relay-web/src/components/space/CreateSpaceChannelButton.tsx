import { useState } from 'react';
import { Modal } from '../../ui/Modal.tsx';
import { CreateSpaceChannelForm } from './CreateSpaceChannelForm.tsx';

export function CreateSpaceChannelButton({
    spaceId,
    onSuccess,
}: {
    spaceId: string;
    onSuccess: () => void;
}) {
    const [isOpen, setIsOpen] = useState<boolean>(false);

    function handleClose() {
        setIsOpen(false);
    }

    function modalOpen() {
        setIsOpen(true);
    }

    function handleCreate() {
        onSuccess();
        handleClose();
    }

    return (
        <div>
            <button onClick={modalOpen}>채널 생성</button>
            <Modal open={isOpen} onClose={handleClose}>
                <CreateSpaceChannelForm
                    onCreate={handleCreate}
                    spaceId={spaceId}
                />
            </Modal>
        </div>
    );
}
