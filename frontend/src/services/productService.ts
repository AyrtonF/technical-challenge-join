import api from './api'
import { Product, ProductPage } from '../types/product'

const ProductService = {
  async getAll(page: number = 0, size: number = 10, search: string = ''): Promise<ProductPage> {
    const response = await api.get('', {
      params: { page, size, search },
    })
    return response.data
  },

  async getById(id: string): Promise<Product> {
    const response = await api.get(`/${id}`)
    return response.data
  },

  async create(product: Omit<Product, 'id'>): Promise<Product> {
    const response = await api.post('', product)
    return response.data
  },

  async update(id: string, product: Product): Promise<Product> {
    const response = await api.put(`/${id}`, product)
    return response.data
  },

  async delete(id: string): Promise<void> {
    await api.delete(`/${id}`)
  },
}

export default ProductService