import { z } from 'zod';

export const userSchema = z.object({
	id: z.string(),
	username: z.string(),
	email: z.string().email()
});
export type User = z.infer<typeof userSchema>;
