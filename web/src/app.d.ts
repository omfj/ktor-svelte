// See https://kit.svelte.dev/docs/types#app

import type { Orca } from '$lib/orca';
import type { User } from '$lib/validators/user';

declare global {
	namespace App {
		// interface Error {}
		interface Locals {
			user: User | undefined;
			orca: Orca;
		}
		// interface PageData {}
		// interface Platform {}
	}
}

export {};
