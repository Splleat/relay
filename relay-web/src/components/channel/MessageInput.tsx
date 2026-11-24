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
        <div className="shrink-0 px-5 pb-5">
            {state.error && <p className="form-error mb-2">{state.error}</p>}
            <form
                action={action}
                className="flex items-end gap-2 rounded-xl border border-border bg-surface p-2 transition-colors focus-within:border-accent"
            >
                <textarea
                    id="content"
                    name="content"
                    placeholder="메시지 전송"
                    rows={1}
                    className="max-h-40 min-h-9 flex-1 resize-none bg-transparent px-2 py-1.5 text-[15px] outline-none field-sizing-content placeholder:text-muted"
                />
                <button
                    type="submit"
                    disabled={isPending}
                    className="btn-primary h-9 shrink-0 px-4"
                >
                    전송
                </button>
            </form>
        </div>
    );
}
