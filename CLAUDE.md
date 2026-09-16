# ArtShelf — Claude Code Rules

## Role
You are a Senior Kotlin/Android developer with a preference for clean,
readable code and modern Android patterns.

## Architecture
- Clean Architecture: data → domain ← presentation (domain has no dependencies)
- MVVM: ViewModels expose `StateFlow<ScreenNameUiState>` sealed classes only
- No business logic in ViewModels; delegate to UseCases in the domain layer
- Repository interfaces defined in domain; implementations live in data

## Stack
- Jetpack Compose for all UI
- Hilt for dependency injection
- Retrofit + OkHttp for networking (Pixabay API)
- Room for local caching (saved library items)
- Coil for image loading
- Coroutines + Flow throughout

## Kotlin Conventions
- PascalCase for classes, camelCase for variables and functions
- Prefix functions with a verb (fetchArt, mapToUiModel, isLoading)
- Boolean variables: isX, hasX, canX
- No magic numbers — define named constants
- Prefer val over var; immutable data classes
- Early returns over nested conditionals
- Functions under 20 lines with a single purpose

## Android-Specific Rules
- ViewModels only hold UI state and delegate to UseCases
- No direct Retrofit/Room calls outside the data layer
- No hardcoded API keys — use local.properties + BuildConfig
- No coroutine launches in the data layer — return Flow or suspend functions
- Use sealed classes for UI state (Loading, Success, Error)

## Compose Performance
- Mark UI state data classes `@Immutable` or `@Stable` so the compiler can skip recomposition
- Avoid creating new lambdas/objects inside composables on every recomposition — hoist with `remember`
- Use `derivedStateOf` for values computed from other state, not raw recalculation in the composable body
- Keep state hoisted at the narrowest scope needed — don't hoist to a parent that causes siblings to recompose unnecessarily
- In `LazyColumn`/`LazyVerticalGrid`, always supply stable `key`s and `contentType` for items
- No heavy computation (formatting, mapping, filtering) inside item composables — do it upstream in the ViewModel/UseCase

## Coroutines & Flow
- Repository/data-layer work runs on `Dispatchers.IO`; CPU-bound mapping runs on `Dispatchers.Default`
- ViewModels scope work with `viewModelScope`; never launch unscoped coroutines
- Use `distinctUntilChanged()` on Flows exposed to the UI to avoid redundant emissions
- Use `conflate()` or `collectLatest` where only the newest value matters (e.g. search-as-you-type)

## Image Loading (Coil)
- Always constrain image requests with `size()` matching the target composable — never load full-resolution images into thumbnails
- Define a shared `ImageLoader` with explicit memory/disk cache policy rather than relying on defaults
- Use placeholder/crossfade consistently; avoid re-decoding on recomposition by keying requests on a stable URL

## Room & Paging
- Use Paging 3 for any list backed by a potentially large or growing dataset (e.g. saved library items)
- Index columns used in `WHERE`/`ORDER BY` clauses
- Avoid N+1 query patterns — use `@Transaction` with relation queries instead of sequential lookups

## Startup & Build
- Use Baseline Profiles for critical user journeys (app open → grid render)
- Define ProGuard/R8 rules for release builds; verify no crashes under minification before shipping

## Error Handling & Resilience
- Define a retry/backoff policy for Pixabay network calls (e.g. exponential backoff on 429/5xx)
- Room acts as the offline cache — UI should degrade to cached data rather than showing a hard error when network fails

## Accessibility
- All interactive elements have `contentDescription` or are marked `null` intentionally for decorative images
- Minimum touch target size of 48dp

## Navigation
- Single-Activity architecture with Navigation Compose
- Screen routes defined as sealed classes/objects, not raw strings

## Testing
- Arrange-Act-Assert for unit tests
- Name variables: inputX, mockX, actualX, expectedX
- Unit test every public UseCase and ViewModel
- Use test doubles for all dependencies

## Don'ts
- No business logic in the data layer
- No Android framework imports in the domain layer
- No lateinit var unless unavoidable