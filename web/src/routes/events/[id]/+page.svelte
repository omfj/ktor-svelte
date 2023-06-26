<script lang="ts">
	import { enhance } from '$app/forms';
	import type { ActionData, PageServerData } from './$types';

	export let data: PageServerData;
	export let form: ActionData;
</script>

<p class="text-xs text-gray-500">{data.event.id}</p>
<h1 class="text-4xl font-medium mb-5">{data.event.title}</h1>

{#if form}
	{#if form.success}
		<p class="text-green-500">Success!</p>
	{:else}
		<p class="text-red-500">Error: {form.message}</p>
	{/if}
{/if}

<div class="flex gap-2">
	<form action="?/register" method="post" use:enhance>
		<button class="border px-3 py-1 rounded hover:bg-black/10" type="submit">Register</button>
	</form>

	<form action="?/unregister" method="post" use:enhance>
		<button class="border px-3 py-1 rounded hover:bg-black/10" type="submit">Unregister</button>
	</form>
</div>
