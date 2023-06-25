<script lang="ts">
	import { login } from '$lib/stores/user';

	const onSubmit = async (e: Event) => {
		e.preventDefault();
		const form = e.target as HTMLFormElement;
		const formData = new FormData(form);

		const username = formData.get('username')?.toString();
		const password = formData.get('password')?.toString();

		if (!username || !password) {
			alert('Please fill out all fields');
			return;
		}

		const user = await login(username, password);

		if (user) {
			alert('Login successful!');
		} else {
			alert('Login failed!');
		}
	};
</script>

<h1 class="text-3xl font-medium mb-5">Login</h1>

<form class="space-y-4" on:submit={onSubmit}>
	<div class="flex flex-col gap-2">
		<label for="username">Username</label>
		<input
			type="text"
			name="username"
			id="username"
			class="border border-gray-300 rounded-md p-2"
		/>
	</div>

	<div class="flex flex-col gap-2">
		<label for="password">Password</label>
		<input
			type="password"
			name="password"
			id="password"
			class="border border-gray-300 rounded-md p-2"
		/>
	</div>

	<div>
		<button type="submit" class="bg-blue-400 text-white rounded-md p-2">Log in</button>
	</div>
</form>
