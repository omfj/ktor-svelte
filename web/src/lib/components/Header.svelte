<script>
	import { enhance } from '$app/forms';
	import { user } from '$lib/stores';
</script>

<header class="p-3 flex justify-between items-center mb-10">
	<h1 class="text-3xl font-black">
		<a href="/">ORCA</a>
	</h1>

	<nav>
		<ul class="flex gap-4 text-sm">
			{#if $user}
				<li>
					<a class="block px-3 py-1 rounded hover:bg-black/10" href="/profile">👤 Profile</a>
				</li>
				<li>
					<form action="/logout" method="POST" use:enhance>
						<button class="block px-3 py-1 rounded hover:bg-black/10" type="submit"
							>🚪 Log out</button
						>
					</form>
				</li>
			{:else}
				<li>
					<a class="block px-3 py-1 rounded hover:bg-black/10" href="/login">🔑 Log in</a>
				</li>
				<li>
					<a class="block px-3 py-1 rounded hover:bg-black/10" href="/register">🔨 Register</a>
				</li>
			{/if}
		</ul>
	</nav>
</header>
