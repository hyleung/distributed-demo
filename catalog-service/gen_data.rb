require 'faker'
require 'json'

catalog = Array.new
result = {:productList => catalog}
for i in 1..100
    item = {
        :id => i.to_s,
        :name => Faker::Commerce.product_name,
        :price => Faker::Commerce.price,
        :description => Faker::Lorem.sentence
    }
    catalog.push(item) 
end
puts result.to_json
