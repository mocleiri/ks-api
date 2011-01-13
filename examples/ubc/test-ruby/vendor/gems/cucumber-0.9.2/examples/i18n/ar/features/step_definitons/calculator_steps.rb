# encoding: utf-8
begin require 'rspec/expectations'; rescue LoadError; require 'spec/expectations'; end
require 'cucumber/formatter/unicode'
$:.unshift(File.dirname(__FILE__) + '/../../lib')
require 'calculator'

Before do
  @calc = Calculator.new
end

After do
end

Given "كتابة $n في الآلة الحاسبة" do |n|
  @calc.push n.to_i
end

When /يتم الضغط على (\w+)/ do |op|
  @result = @calc.send op
end

Then /يظهر (.*) على الشاشة/ do |result|
  @result.should == result.to_f
end
