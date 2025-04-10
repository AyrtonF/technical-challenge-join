import axios, { AxiosPromise } from "axios"
import { ProductData } from "../interfaces/ProductData";
import { useQuery } from "@tanstack/react-query";

const API_URL = 'http://localhost:8080'

const fechData = async(): AxiosPromise<ProductData[]> =>{
    const response = axios.get(API_URL + '/products')
    return response;
}

export function useProductData(){
    const query = useQuery({
        queryFn:fechData,
        queryKey:['product-data'],
        retry:2
    })
    return{
        ...query,
        data: query.data?.data
    }

}