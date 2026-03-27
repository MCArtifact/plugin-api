# Developer Workflow & Commit Guidelines

## 1. Credentials Configuration

For GitHub authentication, always prioritize Agent-specific environment variables using the following structure:

```gradle
credentials {
    username = System.getenv('GITHUB_ACTOR') ?: System.getenv('AGENT_GITHUB_NAME') ?: System.getenv('USER_GITHUB_NAME')
    password = System.getenv('GITHUB_TOKEN') ?: System.getenv('AGENT_GITHUB_TOKEN') ?: System.getenv('USER_GITHUB_TOKEN')
}
```

## 2. Branch Naming Convention (STRICT)

**CRITICAL:** Branches must follow the type/noun structure. Do not use verbs or Jira IDs.

**Format:** `{type}/{primary-noun}` or `{type}/{primary-noun}-{secondary-noun}`

**Allowed Types:** `feat/`, `fix/`, `docs/`, `style/`, `refactor/`, `perf/`, `test/`, `build/`, `ci/`, `chore/` and `revert/` (following the Conventional Commits vocabulary). You may add other team-specific types if they stay within the type/noun scheme and are documented here.

**Examples:** `feat/repository-alignment`, `fix/buffer-overflow`, `ci/update-workflows`

We mirror the Conventional Commits intent: the branch type signals the broad purpose (feature, fix, docs, refactor, infrastructure, etc.), the noun describes the subject, and scopes or prefixes are not allowed in the branch name itself.

Branch names must reflect the entire set of changes on the branch; they should not describe only a subset of the work.

Always ensure your current branch is not `master`. If you find yourself on master, create a new branch before making changes because direct work on master is forbidden.

Before starting work, always confirm you are on the correct branch.

The remote repository prevents pushing to master, so you must always submit a pull request for merging instead of pushing directly.

## 3. Commit Message Convention (STRICT)

**CRITICAL:** Follow this exact structure:

**Format:**

```markdown
type: {short description}

{detailed body explaining the 'why' and 'how'}
```

**Example:**

```markdown
feat: implement asynchronous data fetching interface

Integrated IDataProvider from plugin-api

Added thread-safe buffer logic in plugin-common
```

Commits must fully represent each individual change or otherwise cover the whole change set within the same scope; avoid splitting a logical change across multiple commits or bundling unrelated fixes together.

**Scope (optional):** When your change targets a specific subsystem, append a noun in parentheses immediately after the type, like `feat(auth):` or `fix(ui/core):`. The scope clarifies which part of the code was affected without cluttering branch names, which continue to be type/noun.

## 4. Agent Validation & Communication

- **PR Delivery:** Titles and descriptions for PRs must always be provided inside a markdown code box for easy copying.
- **PR Content:** Use human-readable language. Do not use git conventions (like `feat:`) for PR titles.
- **PR Coverage:** Ensure every PR title and description relates to the entire set of changes within the current branch so the summary reflects all work that was done.
- **Push follow-up:** If the user asks for a push, then after a successful push always provide the PR title, description, and link.
- **Pre-execution Check:** Before running `git checkout -b` or `git commit`, verify:
  - Branch is type/noun.
  - Commit header has colon.
  - Java files use PascalCase.
  - API Enums are utilized wherever available.
  - All written code/commits are in English.
  - Use MCP Tool if available

## 5. Instruction Discovery Priority

When you need to read instruction files, always follow this search order:

1. `AGENTS.md` inside the current project (if it exists).
2. `agents-instructions` repository clone (if the directory exists).
