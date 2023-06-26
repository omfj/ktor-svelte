import { writable } from 'svelte/store';
import type { User } from './validators/user';

export const user = writable<User | undefined>(undefined);
