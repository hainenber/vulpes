import type { DataProvider } from "@refinedev/core";

const API_URL = "http://localhost:8080";

export const dataProvider: DataProvider = {
    getOne: () => {
        throw new Error("Not implemented");
    },
    update: () => {
        throw new Error("Not implemented");
    },
    getList: async ({ resource, pagination, sorters }) => {
        // Add URL params for pagination, filtering and sorting.
        const params = new URLSearchParams();
        if (pagination?.current && pagination?.pageSize) {
            params.append("page", String(pagination.current));
            params.append("size", String(pagination.pageSize));
        }
        if (sorters && sorters.length > 0) {
            params.append(
                "sortBy",
                sorters.map((sorter) => sorter.field).join(","),
            );
            params.append(
                "order",
                sorters.map((sorter) => sorter.order).join(","),
            );
        }

        // Fetching data from server.
        const response = await fetch(
            `${API_URL}/${resource}?${params.toString()}`,
        );
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
