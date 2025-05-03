import type { DataProvider } from "@refinedev/core";

const API_URL = "http://localhost:8080";

export const dataProvider: DataProvider = {
    getOne: async ({ resource, id }) => {
        const response = await fetch(`${API_URL}/${resource}/${id}`);
        if (response.status < 200 || response.status > 299) {
            throw response;
        }
        const data = await response.json();
        return { data };
    },
    update: () => {
        throw new Error("Not implemented");
    },
    getList: async ({ resource }) => {
        const response = await fetch(`${API_URL}/${resource}`);
        if (response.status < 200 || response.status > 299) {
            throw response;
        }
        const data = (await response.json()) ?? [];
        return { data, total: data?.length };
    },
    create: () => {
        throw new Error("Not implemented");
    },
    deleteOne: () => {
        throw new Error("Not implemented");
    },
    getApiUrl: () => API_URL,
};
