import axios, { AxiosPromise } from "axios"
import { ProductData } from "../interfaces/ProductData";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const API_URL = 'http://localhost:8080'

const postData = async(data: ProductData): AxiosPromise<any> =>{
    const response = axios.post(API_URL + '/products', data)
    return response;
}

export function useProductUpdateData(){
const queryClient = useQueryClient();

    const update = useMutation({
        mutationFn:postData,
        retry:2,
        onSuccess: ()=>{
            queryClient.invalidateQueries({ 
                queryKey: ['product-data'] 
            });
        }
    })
    return update;
}