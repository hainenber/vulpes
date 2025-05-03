const globals = require("globals");
const { defineConfig, globalIgnores } = require("eslint/config");
const typescriptParser = require("@typescript-eslint/parser");
const reactRefreshPlugin = require("eslint-plugin-react-refresh");
const recommendedTypescriptLinterPlugin = require("@typescript-eslint/eslint-plugin");
const recommendedReactHooksPlugin = require("eslint-plugin-react-hooks");

module.exports = defineConfig([
    // Ignore dist/ folder and own ESLint config from being linted.
    globalIgnores(["**/dist/"]),

    // Migrate from original refine.dev-scaffolded ESLint 8 config.
    {
        languageOptions: {
            ecmaVersion: "latest",
            sourceType: "module",
            globals: {
                ...globals.browser,
            },
            parser: typescriptParser,
        },

        plugins: {
            "react-refresh": reactRefreshPlugin,
            "react-hooks/recommended": recommendedReactHooksPlugin,
            "@typescript-eslint/recommended": recommendedTypescriptLinterPlugin,
        },

        // Override the recommended config.
        rules: {
            "react-refresh/only-export-components": "warn",
        },
    },
]);
