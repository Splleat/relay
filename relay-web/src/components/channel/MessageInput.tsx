import { useActionState } from 'react';
import type { FormState } from '../../types/common.ts';
import { type MessageResponse, sendMessage } from '../../api/message.ts';
import { getErrorMessage } from '../../api/errors.ts';

export function MessageInput({
    channelId,
    onSent,
}: {
    channelId: string;
    onSent: (message: MessageResponse) => void;
}) {
    const [state, action, isPending] = useActionState(
        async (
            _prevState: FormState,
            formData: FormData,
        ): Promise<FormState> => {
            const content = formData.get('content') as string;

            if (!content.trim()) {
                return { error: '메시지를 입력해주세요.' };
            }

            const idempotencyKey = crypto.randomUUID();

            try {
                const response = await sendMessage(channelId, {
                    content,
                    idempotencyKey,
                });

                onSent(response.message);

                return { error: null };
            } catch (error) {
                return { error: getErrorMessage(error) };
            }
        },
        { error: null },
    );
    return (
        <div className="flex flex-col">
            {state.error && <p className="text-red-500">{state.error}</p>}
            <div className="flex flex-row">
                <form action={action}>
                    <textarea
                        id="content"
                        name="content"
                        placeholder="메시지 전송"
                    />
                    <button type="submit" disabled={isPending}>
                        전송
                    </button>
                </form>
            </div>
        </div>
    );
}
