import { useActionState, useId } from 'react';
import { createSpace } from '../../api/space.ts';
import { getErrorMessage } from '../../api/errors.ts';
import type { FormState } from '../../types/common.ts';

export function CreateSpaceForm({ onCreate }: { onCreate: () => void }) {
    const [state, action, isPending] = useActionState(
        async (_prevState: FormState, formData: FormData) => {
            try {
                const name = formData.get('name') as string;

                await createSpace({ name });

                onCreate();

                return { error: null };
            } catch (error) {
                const errorMessage = getErrorMessage(error);

                return { error: errorMessage };
            }
        },
        { error: null },
    );

    const id = useId();

    return (
        <div>
            <h2 className="text-lg font-semibold">새 스페이스</h2>
            <form action={action} className="mt-4 flex flex-col gap-4">
                <div>
                    <label htmlFor={id} className="field-label">
                        스페이스 이름
                    </label>
                    <input id={id} name="name" required className="input" />
                </div>
                {state.error && <p className="form-error">{state.error}</p>}
                <button
                    type="submit"
                    disabled={isPending}
                    className="btn-primary self-end"
                >
                    {isPending ? '생성 중...' : '생성'}
                </button>
            </form>
        </div>
    );
}
