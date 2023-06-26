<script lang="ts">
	import { formatDate } from '$lib/date';
	import type { PageServerData } from './$types';

	export let data: PageServerData;
</script>

{#if data.happenings?.length}
	<h2 class="text-2xl font-medium mb-5">Kommende hendelser</h2>

	<ul class="flex flex-col gap-2">
		{#each data.happenings as event}
			<li>
				<a href="/events/{event.id}">
					<div class="bg-black/10 hover:bg-black/20 p-3 rounded-lg">
						<h1 class="text-2xl">{event.title}</h1>
						<p>{formatDate(event.date)}</p>
					</div>
				</a>
			</li>
		{/each}
	</ul>
{:else}
	<p>Ingen kommende hendelser</p>
{/if}
